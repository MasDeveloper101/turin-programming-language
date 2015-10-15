package me.tomassetti.turin.typesystem;

import me.tomassetti.turin.parser.analysis.resolvers.SymbolResolver;

public class ReflectionBasedTypeDefinition extends TypeDefinition {

    private Class<?> clazz;

    public ReflectionBasedTypeDefinition(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getCanonicalName() {
        return clazz.getCanonicalName();
    }

    @Override
    public boolean isInterface() {
        return clazz.isInterface();
    }

    @Override
    public boolean isClass() {
        return !clazz.isInterface() && !clazz.isEnum() && !clazz.isAnnotation() && !clazz.isArray() && !clazz.isPrimitive();
    }

    @Override
    public TypeDefinition getSuperclass(SymbolResolver resolver) {
        throw new UnsupportedOperationException();
    }
}
