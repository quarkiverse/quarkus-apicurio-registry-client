package io.quarkiverse.apicurio.deployment;

import org.jboss.jandex.IndexView;

import io.apicurio.registry.client.service.ArtifactsService;
import io.apicurio.registry.client.service.IdsService;
import io.apicurio.registry.client.service.RulesService;
import io.apicurio.registry.client.service.SearchService;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.CombinedIndexBuildItem;
import io.quarkus.deployment.builditem.ExtensionSslNativeSupportBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.nativeimage.NativeImageProxyDefinitionBuildItem;
import io.quarkus.deployment.builditem.nativeimage.ReflectiveClassBuildItem;

class QuarkiverseApicurioRegistryClientProcessor {

    private static final String FEATURE = "quarkiverse-apicurio-registry-client";

    private static final String[] DTO_PACKAGES = new String[]{
        "io.apicurio.registry.rest.beans",
        "io.apicurio.registry.types"
    };

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void httpProxies(BuildProducer<NativeImageProxyDefinitionBuildItem> proxyProducer) {
        proxyProducer.produce(new NativeImageProxyDefinitionBuildItem(ArtifactsService.class.getName()));
        proxyProducer.produce(new NativeImageProxyDefinitionBuildItem(IdsService.class.getName()));
        proxyProducer.produce(new NativeImageProxyDefinitionBuildItem(RulesService.class.getName()));
        proxyProducer.produce(new NativeImageProxyDefinitionBuildItem(SearchService.class.getName()));

    }

    @BuildStep
    void sslSupport(BuildProducer<ExtensionSslNativeSupportBuildItem> extensionSslNativeSupport) {
        extensionSslNativeSupport.produce(new ExtensionSslNativeSupportBuildItem(FEATURE));
    }

    @BuildStep
    ReflectiveClassBuildItem registerForReflection(CombinedIndexBuildItem combinedIndex) {
        IndexView index = combinedIndex.getIndex();

        String[] dtos = index.getKnownClasses().stream()
                .map(ci -> ci.name().toString())
                .filter(n -> isDtoClass(n))
                .sorted()
                .toArray(String[]::new);

        return new ReflectiveClassBuildItem(false, true, dtos);
    }

    private boolean isDtoClass(String name) {
        for (String pkg : DTO_PACKAGES) {
            if (name.startsWith(pkg)) {
                return true;
            }
        }
        return false;
    }

}
