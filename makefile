.PHONY: build jar xform-test xform-test-only test run test-only run-only test-simple-only test-simple

TEST_FILE=/Users/joeflack4/projects/xform-test/test/files/XformTest/example_output/XformTest1.xml
TEST_FILE_TEMP1=/Users/joeflack4/projects/xform-test/test/files/XformTest/example_output/XformTest1_removed_deviceId.xml
TEST_FILE_TEMP2=/Users/joeflack4/projects/xform-test/test/files/XformTest/example_output/XformTest1_removed_deviceId-pulldata.xml
SIMPLE_FORM=resources/simple-form.xml

# Building
build:
	gradle build
jar:
	gradle jar && \
	cp build/libs/*.jar ../xform-test/xform_test/bin/*.jar

# Development
xform-test-only-base:
	java -jar build/libs/opendatakit-javarosa-2.11.0.jar ${FILE_PATH}

xform-test-only:
	make xform-test-only-base FILE_PATH=${TEST_FILE}
xform-test:
	make build && \
    make jar && \
    make xform-test-only

test: xform-test
run: xform-test
test-only: xform-test-only
run-only: xform-test-only

# Temp
test-simple-only:
	make xform-test-only-base FILE_PATH=${SIMPLE_FORM}
test-simple:
	make build && \
    make jar && \
    make test-simple-only

test-temp1-only:
	make xform-test-only-base FILE_PATH=${TEST_FILE_TEMP1}
test-temp1:
	make build && \
    make jar && \
    make test-temp1-only

test-temp2-only:
	make xform-test-only-base FILE_PATH=${TEST_FILE_TEMP2}
test-temp2:
	make build && \
    make jar && \
    make test-temp2-only
