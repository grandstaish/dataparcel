package nz.bradcampbell.paperparcel;

import com.google.common.base.Joiner;
import com.google.testing.compile.JavaFileObjects;
import javax.tools.JavaFileObject;
import org.junit.Test;

import static com.google.common.truth.Truth.assertAbout;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class ListTests {

  @Test public void listOfParcelableTypesTest() throws Exception {
    JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n')
        .join("package test;", "import nz.bradcampbell.paperparcel.PaperParcel;",
            "import java.util.List;", "@PaperParcel", "public final class Test {",
            "private final List<Integer> child;", "public Test(List<Integer> child) {",
            "this.child = child;", "}", "public List<Integer> getChild() {", "return this.child;",
            "}", "}"));

    JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestParcel",
        Joiner.on('\n')
            .join("package test;", "import android.os.Parcel;", "import android.os.Parcelable;",
                "import java.lang.Integer;", "import java.lang.Override;",
                "import java.util.ArrayList;", "import java.util.List;",
                "import nz.bradcampbell.paperparcel.TypedParcelable;",
                "public final class TestParcel implements TypedParcelable<Test> {",
                "public static final Parcelable.Creator<TestParcel> CREATOR = new Parcelable.Creator<TestParcel>() {",
                "@Override public TestParcel createFromParcel(Parcel in) {",
                "List<Integer> outChild = null;", "if (in.readInt() == 0) {",
                "int childSize = in.readInt();",
                "List<Integer> child = new ArrayList<Integer>(childSize);",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Integer outChildItem = null;", "if (in.readInt() == 0) {",
                "outChildItem = in.readInt();", "}", "child.add(outChildItem);", "}",
                "outChild = child;", "}", "Test data = new Test(outChild);",
                "return new TestParcel(data);", "}",
                "@Override public TestParcel[] newArray(int size) {",
                "return new TestParcel[size];", "}", "};", "private final Test data;",
                "public TestParcel(Test data) {", "this.data = data;", "}",
                "@Override public Test get() {", "return this.data;", "}",
                "@Override public int describeContents() {", "return 0;", "}",
                "@Override public void writeToParcel(Parcel dest, int flags) {",
                "List<Integer> child = this.data.getChild();", "if (child == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);",
                "int childSize = child.size();", "dest.writeInt(childSize);",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Integer childItem = child.get(childIndex);", "if (childItem == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);", "dest.writeInt(childItem);",
                "}", "}", "}", "}", "}"));

    assertAbout(javaSource()).that(source)
        .processedWith(new PaperParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedSource);
  }

  @Test public void listOfListOfBundlesTest() throws Exception {
    JavaFileObject dataClass = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n')
        .join("package test;", "import nz.bradcampbell.paperparcel.PaperParcel;",
            "import android.os.Bundle;", "import java.util.List;", "@PaperParcel",
            "public final class Test {", "private final List<List<Bundle>> child;",
            "public Test(List<List<Bundle>> child) {", "this.child = child;", "}",
            "public List<List<Bundle>> getChild() {", "return this.child;", "}", "}"));

    JavaFileObject parcel = JavaFileObjects.forSourceString("test/TestParcel", Joiner.on('\n')
        .join("package test;", "import android.os.Bundle;", "import android.os.Parcel;",
            "import android.os.Parcelable;", "import java.lang.ClassLoader;",
            "import java.lang.Override;", "import java.util.ArrayList;", "import java.util.List;",
            "import nz.bradcampbell.paperparcel.TypedParcelable;",
            "public final class TestParcel implements TypedParcelable<Test> {",
            "private static final ClassLoader CLASS_LOADER = Test.class.getClassLoader();",
            "public static final Parcelable.Creator<TestParcel> CREATOR = new Parcelable.Creator<TestParcel>() {",
            "@Override public TestParcel createFromParcel(Parcel in) {",
            "List<List<Bundle>> outChild = null;", "if (in.readInt() == 0) {",
            "int childSize = in.readInt();",
            "List<List<Bundle>> child = new ArrayList<List<Bundle>>(childSize);",
            "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
            "List<Bundle> outChildItem = null;", "if (in.readInt() == 0) {",
            "int childItemSize = in.readInt();",
            "List<Bundle> childItem = new ArrayList<Bundle>(childItemSize);",
            "for (int childItemIndex = 0; childItemIndex < childItemSize; childItemIndex++) {",
            "Bundle outChildItemItem = null;", "if (in.readInt() == 0) {",
            "outChildItemItem = in.readBundle(CLASS_LOADER);", "}",
            "childItem.add(outChildItemItem);", "}", "outChildItem = childItem;", "}",
            "child.add(outChildItem);", "}", "outChild = child;", "}",
            "Test data = new Test(outChild);", "return new TestParcel(data);", "}",
            "@Override public TestParcel[] newArray(int size) {", "return new TestParcel[size];",
            "}", "};", "private final Test data;", "public TestParcel(Test data) {",
            "this.data = data;", "}", "@Override public Test get() {", "return this.data;", "}",
            "@Override public int describeContents() {", "return 0;", "}",
            "@Override public void writeToParcel(Parcel dest, int flags) {",
            "List<List<Bundle>> child = this.data.getChild();", "if (child == null) {",
            "dest.writeInt(1);", "} else {", "dest.writeInt(0);", "int childSize = child.size();",
            "dest.writeInt(childSize);",
            "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
            "List<Bundle> childItem = child.get(childIndex);", "if (childItem == null) {",
            "dest.writeInt(1);", "} else {", "dest.writeInt(0);",
            "int childItemSize = childItem.size();", "dest.writeInt(childItemSize);",
            "for (int childItemIndex = 0; childItemIndex < childItemSize; childItemIndex++) {",
            "Bundle childItemItem = childItem.get(childItemIndex);", "if (childItemItem == null) {",
            "dest.writeInt(1);", "} else {", "dest.writeInt(0);",
            "dest.writeBundle(childItemItem);", "}", "}", "}", "}", "}", "}", "}"));

    assertAbout(javaSource()).that(dataClass)
        .processedWith(new PaperParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(parcel);
  }

  @Test public void listOfParcelableMapsTest() throws Exception {
    JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n')
        .join("package test;", "import nz.bradcampbell.paperparcel.PaperParcel;",
            "import java.util.List;", "import java.util.Map;", "@PaperParcel",
            "public final class Test {", "private final List<Map<Integer, Integer>> child;",
            "public Test(List<Map<Integer, Integer>> child) {", "this.child = child;", "}",
            "public List<Map<Integer, Integer>> getChild() {", "return this.child;", "}", "}"));

    JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestParcel",
        Joiner.on('\n')
            .join("package test;", "import android.os.Parcel;", "import android.os.Parcelable;",
                "import java.lang.Integer;", "import java.lang.Override;",
                "import java.util.ArrayList;", "import java.util.LinkedHashMap;",
                "import java.util.List;", "import java.util.Map;",
                "import nz.bradcampbell.paperparcel.TypedParcelable;",
                "public final class TestParcel implements TypedParcelable<Test> {",
                "public static final Parcelable.Creator<TestParcel> CREATOR = new Parcelable.Creator<TestParcel>() {",
                "@Override public TestParcel createFromParcel(Parcel in) {",
                "List<Map<Integer, Integer>> outChild = null;", "if (in.readInt() == 0) {",
                "int childSize = in.readInt();",
                "List<Map<Integer, Integer>> child = new ArrayList<Map<Integer, Integer>>(childSize);",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Map<Integer, Integer> outChildItem = null;", "if (in.readInt() == 0) {",
                "int childItemSize = in.readInt();",
                "Map<Integer, Integer> childItem = new LinkedHashMap<Integer, Integer>(childItemSize);",
                "for (int childItemIndex = 0; childItemIndex < childItemSize; childItemIndex++) {",
                "Integer outChildItemKey = null;", "if (in.readInt() == 0) {",
                "outChildItemKey = in.readInt();", "}", "Integer outChildItemValue = null;",
                "if (in.readInt() == 0) {", "outChildItemValue = in.readInt();", "}",
                "childItem.put(outChildItemKey, outChildItemValue);", "}",
                "outChildItem = childItem;", "}", "child.add(outChildItem);", "}",
                "outChild = child;", "}", "Test data = new Test(outChild);",
                "return new TestParcel(data);", "}",
                "@Override public TestParcel[] newArray(int size) {",
                "return new TestParcel[size];", "}", "};", "private final Test data;",
                "public TestParcel(Test data) {", "this.data = data;", "}",
                "@Override public Test get() {", "return this.data;", "}",
                "@Override public int describeContents() {", "return 0;", "}",
                "@Override public void writeToParcel(Parcel dest, int flags) {",
                "List<Map<Integer, Integer>> child = this.data.getChild();", "if (child == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);",
                "int childSize = child.size();", "dest.writeInt(childSize);",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Map<Integer, Integer> childItem = child.get(childIndex);",
                "if (childItem == null) {", "dest.writeInt(1);", "} else {", "dest.writeInt(0);",
                "dest.writeInt(childItem.size());",
                "for (Map.Entry<Integer, Integer> childItemEntry : childItem.entrySet()) {",
                "if (childItemEntry.getKey() == null) {", "dest.writeInt(1);", "} else {",
                "dest.writeInt(0);", "dest.writeInt(childItemEntry.getKey());", "}",
                "if (childItemEntry.getValue() == null) {", "dest.writeInt(1);", "} else {",
                "dest.writeInt(0);", "dest.writeInt(childItemEntry.getValue());", "}", "}", "}",
                "}", "}", "}", "}"));

    assertAbout(javaSource()).that(source)
        .processedWith(new PaperParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedSource);
  }

  @Test public void arrayListOfParcelableTypesTest() throws Exception {
    JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n')
        .join("package test;", "import nz.bradcampbell.paperparcel.PaperParcel;",
            "import java.util.ArrayList;", "@PaperParcel", "public final class Test {",
            "private final ArrayList<Integer> child;", "public Test(ArrayList<Integer> child) {",
            "this.child = child;", "}", "public ArrayList<Integer> getChild() {",
            "return this.child;", "}", "}"));

    JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestParcel",
        Joiner.on('\n')
            .join("package test;", "import android.os.Parcel;", "import android.os.Parcelable;",
                "import java.lang.Integer;", "import java.lang.Override;",
                "import java.util.ArrayList;",
                "import nz.bradcampbell.paperparcel.TypedParcelable;",
                "public final class TestParcel implements TypedParcelable<Test> {",
                "public static final Parcelable.Creator<TestParcel> CREATOR = new Parcelable.Creator<TestParcel>() {",
                "@Override public TestParcel createFromParcel(Parcel in) {",
                "ArrayList<Integer> outChild = null;", "if (in.readInt() == 0) {",
                "int childSize = in.readInt();",
                "ArrayList<Integer> child = new ArrayList<Integer>();",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Integer outChildItem = null;", "if (in.readInt() == 0) {",
                "outChildItem = in.readInt();", "}", "child.add(outChildItem);", "}",
                "outChild = child;", "}", "Test data = new Test(outChild);",
                "return new TestParcel(data);", "}",
                "@Override public TestParcel[] newArray(int size) {",
                "return new TestParcel[size];", "}", "};", "private final Test data;",
                "public TestParcel(Test data) {", "this.data = data;", "}",
                "@Override public Test get() {", "return this.data;", "}",
                "@Override public int describeContents() {", "return 0;", "}",
                "@Override public void writeToParcel(Parcel dest, int flags) {",
                "ArrayList<Integer> child = this.data.getChild();", "if (child == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);",
                "int childSize = child.size();", "dest.writeInt(childSize);",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Integer childItem = child.get(childIndex);", "if (childItem == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);", "dest.writeInt(childItem);",
                "}", "}", "}", "}", "}"));

    assertAbout(javaSource()).that(source)
        .processedWith(new PaperParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedSource);
  }

  @Test public void linkedListOfParcelableTypesTest() throws Exception {
    JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n')
        .join("package test;", "import nz.bradcampbell.paperparcel.PaperParcel;",
            "import java.util.LinkedList;", "@PaperParcel", "public final class Test {",
            "private final LinkedList<Integer> child;", "public Test(LinkedList<Integer> child) {",
            "this.child = child;", "}", "public LinkedList<Integer> getChild() {",
            "return this.child;", "}", "}"));

    JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestParcel",
        Joiner.on('\n')
            .join("package test;", "import android.os.Parcel;", "import android.os.Parcelable;",
                "import java.lang.Integer;", "import java.lang.Override;",
                "import java.util.LinkedList;",
                "import nz.bradcampbell.paperparcel.TypedParcelable;",
                "public final class TestParcel implements TypedParcelable<Test> {",
                "public static final Parcelable.Creator<TestParcel> CREATOR = new Parcelable.Creator<TestParcel>() {",
                "@Override public TestParcel createFromParcel(Parcel in) {",
                "LinkedList<Integer> outChild = null;", "if (in.readInt() == 0) {",
                "int childSize = in.readInt();",
                "LinkedList<Integer> child = new LinkedList<Integer>();",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Integer outChildItem = null;", "if (in.readInt() == 0) {",
                "outChildItem = in.readInt();", "}", "child.add(outChildItem);", "}",
                "outChild = child;", "}", "Test data = new Test(outChild);",
                "return new TestParcel(data);", "}",
                "@Override public TestParcel[] newArray(int size) {",
                "return new TestParcel[size];", "}", "};", "private final Test data;",
                "public TestParcel(Test data) {", "this.data = data;", "}",
                "@Override public Test get() {", "return this.data;", "}",
                "@Override public int describeContents() {", "return 0;", "}",
                "@Override public void writeToParcel(Parcel dest, int flags) {",
                "LinkedList<Integer> child = this.data.getChild();", "if (child == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);",
                "int childSize = child.size();", "dest.writeInt(childSize);",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Integer childItem = child.get(childIndex);", "if (childItem == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);", "dest.writeInt(childItem);",
                "}", "}", "}", "}", "}"));

    assertAbout(javaSource()).that(source)
        .processedWith(new PaperParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedSource);
  }

  @Test public void linkedListOfIntegersTest() throws Exception {
    JavaFileObject source = JavaFileObjects.forSourceString("test.Test", Joiner.on('\n')
        .join("package test;", "import nz.bradcampbell.paperparcel.PaperParcel;",
            "import java.util.LinkedList;", "@PaperParcel", "public final class Test {",
            "private final LinkedList<Integer> child;", "public Test(LinkedList<Integer> child) {",
            "this.child = child;", "}", "public LinkedList<Integer> getChild() {",
            "return this.child;", "}", "}"));

    JavaFileObject expectedSource = JavaFileObjects.forSourceString("test/TestParcel",
        Joiner.on('\n')
            .join("package test;", "import android.os.Parcel;", "import android.os.Parcelable;",
                "import java.lang.Integer;", "import java.lang.Override;",
                "import java.util.LinkedList;",
                "import nz.bradcampbell.paperparcel.TypedParcelable;",
                "public final class TestParcel implements TypedParcelable<Test> {",
                "public static final Parcelable.Creator<TestParcel> CREATOR = new Parcelable.Creator<TestParcel>() {",
                "@Override public TestParcel createFromParcel(Parcel in) {",
                "LinkedList<Integer> outChild = null;", "if (in.readInt() == 0) {",
                "int childSize = in.readInt();",
                "LinkedList<Integer> child = new LinkedList<Integer>();",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Integer outChildItem = null;", "if (in.readInt() == 0) {",
                "outChildItem = in.readInt();", "}", "child.add(outChildItem);", "}",
                "outChild = child;", "}", "Test data = new Test(outChild);",
                "return new TestParcel(data);", "}",
                "@Override public TestParcel[] newArray(int size) {",
                "return new TestParcel[size];", "}", "};", "private final Test data;",
                "public TestParcel(Test data) {", "this.data = data;", "}",
                "@Override public Test get() {", "return this.data;", "}",
                "@Override public int describeContents() {", "return 0;", "}",
                "@Override public void writeToParcel(Parcel dest, int flags) {",
                "LinkedList<Integer> child = this.data.getChild();", "if (child == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);",
                "int childSize = child.size();", "dest.writeInt(childSize);",
                "for (int childIndex = 0; childIndex < childSize; childIndex++) {",
                "Integer childItem = child.get(childIndex);", "if (childItem == null) {",
                "dest.writeInt(1);", "} else {", "dest.writeInt(0);", "dest.writeInt(childItem);",
                "}", "}", "}", "}", "}"));

    assertAbout(javaSource()).that(source)
        .processedWith(new PaperParcelProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(expectedSource);
  }
}
