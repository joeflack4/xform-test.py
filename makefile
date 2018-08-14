.PHONY: build jar xform-test xform-test-only test run test-only run-only test-simple-only test-simple

TEST_FILE=/Users/joeflack4/projects/xform-test/test/files/XformTest/output/XFormTest1.xml

# Building
build:
	gradle build
jar:
	gradle jar; \
	cp build/libs/*.jar ../xform-test/xform_test/bin/*.jar

# Testing
update-xml:
	xls2xform /Users/joeflack4/projects/xform-test/test/files/XformTest/input/XFormTest1.xlsx ${TEST_FILE}
xform-test-only-base:
	java -jar build/libs/opendatakit-javarosa-2.11.0.jar ${FILE_PATH}

xform-test-only:
	make xform-test-only-base FILE_PATH=${TEST_FILE}
xform-test:
	make update-xml; \
	make build; \
    make jar; \
    make xform-test-only

test: xform-test
run: xform-test
test-only: xform-test-only
run-only: xform-test-only

# Version control
backup:
	cp -r src/org/javarosa/xform_test "../_archive/backup/javarosa/$(shell date '+%Y-%m-%d %H:%M:%S')"
