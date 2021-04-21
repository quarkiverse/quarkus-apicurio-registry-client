package io.quarkiverse.apicurio.runtime.graal;

import java.lang.reflect.Method;

import com.oracle.svm.core.annotate.Alias;
import com.oracle.svm.core.annotate.Substitute;
import com.oracle.svm.core.annotate.TargetClass;

// substitutions for Retrofit 2.9.0, the version used by Apicurio Registry 1.3.2.Final client libraries

@TargetClass(className = "retrofit2.Platform")
final class Target_retrofit2_Platform {
    @Substitute
    private static Target_retrofit2_Platform findPlatform() {
        return new Target_retrofit2_Platform(true);
    }

    @Alias
    private boolean hasJava8Types;

    @Substitute
    Target_retrofit2_Platform(boolean hasJava8Types) {
        this.hasJava8Types = hasJava8Types;
    }

    @Substitute
    Object invokeDefaultMethod(Method method, Class<?> declaringClass, Object object, Object... args) throws Throwable {
        method.setAccessible(true);
        return method.invoke(object, args);
    }
}

class RetrofitSubstitutions {
}
