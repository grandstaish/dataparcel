package nz.bradcampbell.paperparcel.model.properties;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import java.util.Map;
import java.util.Set;
import nz.bradcampbell.paperparcel.model.Property;
import org.jetbrains.annotations.Nullable;

public class PersistableBundleProperty extends Property {
  public PersistableBundleProperty(boolean isNullable, TypeName typeName, String name) {
    super(isNullable, typeName, name);
  }

  @Override protected CodeBlock readFromParcelInner(CodeBlock.Builder block, ParameterSpec in,
      @Nullable FieldSpec classLoader, Map<ClassName, CodeBlock> typeAdaptersMap,
      Set<String> scopedVariableNames) {
    return CodeBlock.of("$N.readPersistableBundle($N)", in, classLoader);
  }

  @Override protected void writeToParcelInner(CodeBlock.Builder block, ParameterSpec dest,
      ParameterSpec flags, CodeBlock sourceLiteral, Map<ClassName, CodeBlock> typeAdaptersMap,
      Set<String> scopedVariableNames) {
    block.addStatement("$N.writePersistableBundle($L)", dest, sourceLiteral);
  }

  @Override public boolean requiresClassLoader() {
    return true;
  }
}
