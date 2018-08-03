.PHONY: build jar

build:
	gradle build

jar:
	gradle jar && \
	cp build/libs/*.jar ../xform-test/xform_test/bin/*.jar
