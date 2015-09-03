package me.tomassetti.turin.parser.ast.typeusage;

import me.tomassetti.turin.parser.analysis.JvmMethodDefinition;
import me.tomassetti.turin.parser.analysis.JvmType;
import me.tomassetti.turin.parser.analysis.Resolver;
import me.tomassetti.turin.parser.ast.Node;

import java.util.List;

public abstract class TypeUsage extends Node {

    public abstract JvmType jvmType(Resolver resolver);

    public boolean isReferenceTypeUsage() {
        return false;
    }

    public ReferenceTypeUsage asReferenceTypeUsage() {
        throw new UnsupportedOperationException();
    }

    public JvmMethodDefinition findMethodFor(String name, List<JvmType> argsTypes, Resolver resolver, boolean staticContext) {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName());
    }

    public boolean canBeAssignedTo(TypeUsage type, Resolver resolver) {
        throw new UnsupportedOperationException(this.getClass().getCanonicalName());
    }

    public boolean isArray() {
        return this instanceof ArrayTypeUsage;
    }

    public boolean isPrimitive() {
        return this instanceof PrimitiveTypeUsage;
    }

    public boolean isReference() {
        return this instanceof ReferenceTypeUsage;
    }
}