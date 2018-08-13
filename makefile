.PHONY: build jar xform-test xform-test-only test run test-only run-only

TEST_FILE=/Users/joeflack4/projects/xform-test/test/files/XformTest/example_output/XformTest1.xml
TEST_FILE2=resources/simple-form.xml

build:
	gradle build

jar:
	gradle jar && \
	cp build/libs/*.jar ../xform-test/xform_test/bin/*.jar

xform-test-only:
	java -jar build/libs/opendatakit-javarosa-2.11.0.jar ${TEST_FILE2}

xform-test:
	make build && \
    make jar && \
    make xform-test-only

test: xform-test
run: xform-test
test-only: xform-test-only
run-only: xform-test-only
