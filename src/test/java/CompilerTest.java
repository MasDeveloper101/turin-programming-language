import me.tomassetti.turin.*;
import me.tomassetti.turin.compiler.ClassFileDefinition;
import me.tomassetti.turin.compiler.Compiler;
import me.tomassetti.turin.ast.*;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by federico on 29/08/15.
 */
public class CompilerTest {

    private TurinFile mangaAst() {
        // define AST
        TurinFile turinFile = new TurinFile();

        NamespaceDefinition namespaceDefinition = new NamespaceDefinition("manga");

        turinFile.setNameSpace(namespaceDefinition);

        ReferenceTypeUsage stringType = new ReferenceTypeUsage("String");
        ReferenceTypeUsage intType = new ReferenceTypeUsage("UInt");

        PropertyDefinition nameProperty = new PropertyDefinition("name", stringType);

        turinFile.add(nameProperty);

        TypeDefinition mangaCharacter = new TypeDefinition("MangaCharacter");
        PropertyDefinition ageProperty = new PropertyDefinition("age", intType);
        PropertyReference nameRef = new PropertyReference("name");
        mangaCharacter.add(nameRef);
        mangaCharacter.add(ageProperty);

        turinFile.add(mangaCharacter);
        return turinFile;
    }

    @Test
    public void compileAstManga() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        TurinFile turinFile = mangaAst();

        // generate bytecode
        Compiler instance = new Compiler();
        List<ClassFileDefinition> classFileDefinitions = instance.compile(turinFile);
        assertEquals(1, classFileDefinitions.size());

        TurinClassLoader turinClassLoader = new TurinClassLoader();
        Class mangaCharacterClass = turinClassLoader.addClass(classFileDefinitions.get(0).getName(),
                classFileDefinitions.get(0).getBytecode());
        assertEquals(1, mangaCharacterClass.getConstructors().length);
        Object ranma = mangaCharacterClass.getConstructors()[0].newInstance("Ranma", 16);

        Method getName = mangaCharacterClass.getMethod("getName");
        assertEquals("Ranma", getName.invoke(ranma));

        Method getAge = mangaCharacterClass.getMethod("getAge");
        assertEquals(16, getAge.invoke(ranma));
    }

    @Test(expected = InvocationTargetException.class)
    public void nullIsNotAcceptedForNameProperty() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        TurinFile turinFile = mangaAst();

        // generate bytecode
        Compiler instance = new Compiler();
        List<ClassFileDefinition> classFileDefinitions = instance.compile(turinFile);
        assertEquals(1, classFileDefinitions.size());

        TurinClassLoader turinClassLoader = new TurinClassLoader();
        Class mangaCharacterClass = turinClassLoader.addClass(classFileDefinitions.get(0).getName(),
                classFileDefinitions.get(0).getBytecode());
        assertEquals(1, mangaCharacterClass.getConstructors().length);
        Object ranma = mangaCharacterClass.getConstructors()[0].newInstance(null, 16);
    }

    @Test(expected = InvocationTargetException.class)
    public void negativeAgeIsNotAcceptedForNameProperty() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        TurinFile turinFile = mangaAst();

        // generate bytecode
        Compiler instance = new Compiler();
        List<ClassFileDefinition> classFileDefinitions = instance.compile(turinFile);
        assertEquals(1, classFileDefinitions.size());

        TurinClassLoader turinClassLoader = new TurinClassLoader();
        Class mangaCharacterClass = turinClassLoader.addClass(classFileDefinitions.get(0).getName(),
                classFileDefinitions.get(0).getBytecode());
        assertEquals(1, mangaCharacterClass.getConstructors().length);
        Object ranma = mangaCharacterClass.getConstructors()[0].newInstance("Ranma", -16);
    }

}
