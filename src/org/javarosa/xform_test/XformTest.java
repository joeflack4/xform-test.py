/*
 * Copyright 2018 Nafundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javarosa.xform_test;


// Uncomment
// 1: these when ready to use current_in_itemset_nodeset_should_refer_to_node()
// 2: used by setUp()
import org.javarosa.core.model.FormDef;
//import org.javarosa.core.model.ItemsetBinding;  // 1
//import org.javarosa.core.model.SelectChoice;  // 1
//import org.javarosa.core.model.data.SelectOneData;  // 1
//import org.javarosa.core.model.data.StringData;  // 1
//import org.javarosa.core.model.data.helper.Selection;  // 1
import org.javarosa.core.model.instance.InstanceInitializationFactory;
//import org.javarosa.core.services.PrototypeManager;  // 2: used by setUp()
//import org.javarosa.form.api.FormEntryController;  // 1
import org.javarosa.form.api.FormEntryModel;
//import org.javarosa.form.api.FormEntryPrompt;  // 1
//import org.javarosa.model.xform.XFormsModule;  // 2: used by setUp()

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// don't need
//import static org.javarosa.xform_test.ResourcePathHelper.r;

// For debugging
//import java.lang.reflect.*;
//import java.net.URL;
//import java.net.URLClassLoader;

//import org.javarosa.xform_test.FormParseInit;
//import org.junit.Before;
//import org.junit.Test;
//
//import static junit.framework.TestCase.assertEquals;


/* TODO
## Priorities
 - (1) below: didn't work. try 2 instead.
 - (2) below: worked
 - (3) below: worked
 - (4) below:

## I. Fix "storage factory" error
dcbriccetti
Are you comfortable looking through the Validate code to see how it calls JavaRosa?
 - https://github.com/opendatakit/validate
That’s it. You can search for some of the calls you are making in your code, to see what else it does. I should be
around for a while if you don’t make progress.

ln
Heya, this is Hélène from Yaw’s phone because I don’t have a device on me. Just don’t want you to hunt too long. I do
think @dcbriccetti is right that reading deviceid is the problem. You could take that question out to double check. (1)

The issue is that a client like Collect specifies an implementation for PropertyManager and tests don’t have any. You’d
 probably want to add a quick mocked one that always returns the same value.

yanokwa
This is Yaw from Yaw’s phone. Look at the Validate code. It does exactly this


dcbriccetti
This, maybe: (2)
// needed to override rms property manager
org.javarosa.core.services.PropertyManager
       .setPropertyManager(new StubPropertyManager());

dcbriccetti
Then copy over StubPropertyManager, I suppose. (3)


# II. Fix "pulldata" error
dcbriccetti
Yes, that does it. Your next problem will be this:
```Exception in thread "main" java.lang.RuntimeException: Error evaluating field 'your_name': The problem was located in
 calculate expression for /XformTest1/your_name
XPath evaluation: cannot handle function 'pulldata'```

ln (4)
Yes, pulldata is not an XForms spec feature, it’s client-specific and implemented in a really odd way. You’ll need to do
 some mocking for that.
 */
public class XFormTest {
    @SuppressWarnings("FieldCanBeLocal")  // Later can remove this suppress
    private FormDef formDef;

    public static void main(String[] args) {
        // TODO: DEBUGGING
        String filePath  = "/Users/joeflack4/projects/xform-test/test/files/XformTest/example_output/XformTest1.xml";
//         String filePath  = .../XformTest1_removed_deviceId.xml";
        // String filePath  = ".../XformTest1_removed_deviceId-pulldata.xml";
        // String filePath  = "...resources/simple-form.xml";
        // String filePath = args[0];
        XFormTest xFormTest = new XFormTest();
        xFormTest.setUp(filePath);
    }

    /* Use like: listMethods(FormParseInit.class) */
//    public static void listMethods(Class c) {
//        // Class c = c.class;
//        Method[] m = c.getDeclaredMethods();
//        for (int i = 0; i < m.length; i++)
//            System.out.println(m[i].toString());
//    }

    /* Comments herein taken from ODK Validate where this code was copied from, unless initialed otherwise. */
    private static void setUpMockClient() {
        // needed to override rms property manager  // What does 'rms' mean? -jef, 2018/10/13
        org.javarosa.core.services.PropertyManager
            .setPropertyManager(new StubPropertyManager());
    }

    private static FormParseInit squelchedFormParseInit(Path path) {
        PrintStream originalStream = System.out;
        PrintStream dummyStream = new PrintStream(new OutputStream(){
            public void write(int b) {}  // NO-OP
        });
        System.setOut(dummyStream);
        FormParseInit fpi = new FormParseInit(path);  // The code to be squelched.
        System.setOut(originalStream);
        return fpi;
    }

    /* Removes pulldata(), which is client specific */
    private static String removePullData(String filePath) {  // TODO 1b: Finish this.
        XmlModifier xml = new XmlModifier(filePath);
        xml.modifyNodeAttributesByFindReplace("calculate", "pulldata", "");
        xml.writeToFile();
        return xml.getnewFilePath();
    }

    private void setUp(String xmlFilePathStr) {
        setUpMockClient();
        String newXmlFilePath = removePullData(xmlFilePathStr);  // TODO 1a: Finish this
        System.out.print(newXmlFilePath);  // temp

        Path xmlFilePath = Paths.get(xmlFilePathStr);  // Change to: newXmlFilePath
        FormParseInit fpi = squelchedFormParseInit(xmlFilePath);  // Change to: newXmlFilePath

        formDef = fpi.getFormDef();

        formDef.initialize(true, new InstanceInitializationFactory());  // Breaks due to pulldata()
        FormEntryModel formEntryModel = new FormEntryModel(formDef);

        //  formEntryController = new FormEntryController(formEntryModel);
        System.out.print(formEntryModel);  // DEBUGGING
        System.out.print("happy joy time");
    }

//    /**
//     * current() in an itemset nodeset expression should refer to the select node. This is verified by building an
//     * itemset from a repeat which is a sibling of the select.
//     */
    // @Test
//    public void current_in_itemset_nodeset_should_refer_to_node() {
//        // don't know how to jump to repeat directly so jump to following question and step backwards
//        formEntryController.jumpToFirstQuestionWithName("selected_person");
//        formEntryController.stepToPreviousEvent(); // repeat
//        formEntryController.descendIntoRepeat(0);
//        formEntryController.stepToNextEvent(); // person
//
//        StringData nameValue = new StringData("Bob");
//        formEntryController.answerQuestion(nameValue, true);
//
//        formEntryController.stepToNextEvent();
//        formEntryController.descendIntoNewRepeat();
//        formEntryController.stepToNextEvent(); // person
//
//        StringData nameValue2 = new StringData("Janet");
//        formEntryController.answerQuestion(nameValue2, true);
//
//        formEntryController.jumpToFirstQuestionWithName("selected_person");
//        FormEntryPrompt personPrompt = formEntryController.getModel().getQuestionPrompt();
//        personPrompt.getAnswerValue();
//        ItemsetBinding dynamicChoices = personPrompt.getQuestion().getDynamicChoices();
//
//        SelectChoice personChoice = dynamicChoices.getChoices().get(1);
//        // assertEquals("Janet", personPrompt.getSelectChoiceText(personChoice));
//
//        SelectOneData personSelection = new SelectOneData(new Selection(personChoice));
//        formEntryController.answerQuestion(personSelection, true);
//
//        SelectOneData personSelectionValue = (SelectOneData) formDef.getFirstDescendantWithName("selected_person")
//            .getValue();
//        // assertEquals("2", personSelectionValue.getDisplayText());
//    }
}
