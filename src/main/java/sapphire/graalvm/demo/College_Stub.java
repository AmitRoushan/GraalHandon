package sapphire.graalvm.demo;

import sapphire.graalvm.serde.*;
import org.graalvm.polyglot.*;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

// This is a stub file for javascript College class.
// This file is hand coded at present. Eventually it
// will be generated automatically by Isaac's generator. 

/**
 * Stub for javascript College class.
 * This stub exposes the same methods as the javascript
 * College class.
 */
public class College_Stub {
    Context polyglot;
    Value collegeTypeJS;
    Value collegeJS;
    Value collegeTypeRuby;
    Value collegeRuby;


    public College_Stub() throws Exception{
        // Use GraalVM polyplot API to create a College instance for java script
        polyglot = Context.newBuilder(new String[] {"js", "ruby"})
                .allowAllAccess(true)
                .build();

        // Build the context from code with JS
        String jsHome = System.getProperty("JS_HOME");
        System.out.println("JS_HOME:" + jsHome);
        polyglot.eval(Source.newBuilder("js", new File(jsHome + "/college.js")).build());

        // Build the context from code with Ruby
        String rubyHome = System.getProperty("RUBY_HOME");
        System.out.println("RUBY_HOME:" + rubyHome);
        polyglot.eval(Source.newBuilder("ruby", new File(rubyHome + "/college.rb")).build());

        // create College class instance for JS
        collegeTypeJS = polyglot.eval("js", "College");
        collegeJS =  collegeTypeJS.newInstance("MichaelJSCollege");

        // create College class instance for ruby
        collegeTypeRuby = polyglot.eval("ruby", "College");
        collegeRuby =  collegeTypeJS.newInstance("MichaelRubyCollege");
    }

    public String getName() throws Exception {

        // 1. Use GraalVM polyplot API to invoke 
        // getName method on the college instance. 
        // 2. Serialize return value to bytes
        // 3. Descerialize bytes into GraalVM Value object

        Value vJS = collegeJS.getMember("getName").execute();
        Value vRuby = collegeRuby.getMember("getName").execute();

        return "JS: " + vJS.asString() + " Ruby: " + vRuby.asString();
    }

    public void addStudent(int id, String name) throws Exception{
        // 1. Serialize student into bytes
        // 2. Deserialize bytes back into GraalVM Value object
        // 3. Use GraalVM polyplot API to invoke
        //    addStudent method on college.

        // Note: if the input parameters is non-primitive this would not work.
        //String s = String.format("new Student(%d, \"%s\")", id, name);
        Value clientStudentJS = polyglot.eval("js", "CSEStudent").newInstance(id, name);
        Value clientStudentRuby = polyglot.eval("ruby", "CSEStudent").newInstance(id, name);


        collegeJS.getMember("addStudent").execute(clientStudentJS);
        collegeRuby.getMember("addStudent").execute(clientStudentRuby);
    }

    public List<Object> getStudents() throws Exception{
        // 1. Use GraalVM polyplot API to invoke 
        // getName method on the college instance. 
        // 2. Serialize return value to bytes
        // 3. Descerialize bytes into GraalVM Value object
        Value serverJSStudents = collegeJS.getMember("getStudents").execute();
        Value serverRubyStudents = collegeRuby.getMember("getStudents").execute();

        System.out.println("Students is proxy object " + serverJSStudents.isProxyObject());
        System.out.println("Students is host object " + serverJSStudents.isHostObject());

        List<Object> finalList = new ArrayList<>();
        List<Object> jsList = serverJSStudents.as(List.class);
        List<Object> rubyList = serverRubyStudents.as(List.class);
        finalList.addAll(jsList);
        finalList.addAll(rubyList);

        System.out.println(serverJSStudents.hasArrayElements());

        for (int i =0 ; i < serverJSStudents.getArraySize() ; i++){
            Value vStud = serverJSStudents.getArrayElement(i);
            System.out.printf(" getBranch -> %s : getID -> %d : getName -> %s : getLang -> %s ",
                    vStud.getMember("getBranch").execute().asString(),
                    vStud.getMember("getID").execute().asInt(),
                    vStud.getMember("getName").execute().asString(),
                    vStud.getMember("getLang").execute().asString());
            System.out.println("");
        }

        for (int i =0 ; i < serverRubyStudents.getArraySize() ; i++){
            Value vStud = serverRubyStudents.getArrayElement(i);
            System.out.printf(" getBranch -> %s : getID -> %d : getName -> %s : getLang -> %s ",
                    vStud.getMember("getBranch").execute().asString(),
                    vStud.getMember("getID").execute().asInt(),
                    vStud.getMember("getName").execute().asString(),
                    vStud.getMember("getLang").execute().asString());
            System.out.println("");
        }

//        for( Object student : finalList){
//            Value vStud = (Value) student;
//            System.out.println(vStud.getMember("getBranch").execute());
//            System.out.println(vStud.getMember("getID").execute());
//            System.out.println(vStud.getMember("getName").execute());
//            System.out.println(vStud.getMember("getLang").execute());
//        }

        return finalList;
    }
}
