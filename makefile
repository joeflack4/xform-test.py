.PHONY: test-pyxform test setup-pyxform setup test-survey-instance \
test-xform-test typical-sphinx-setup open-docs readme-to-docs \
build-docs-no-open build-docs docs-push-production docs-push-staging \
docs-push create-docs docs-create docs-build docs test-unit-tests \
test-files-to-static-doc-files push-docs push-docs-staging docs-open \
push-docs-production

# Setup
setup-pyxform:
	(cd converters/pyxform && \
	python3 setup.py develop)
setup: setup-pyxform

# Test
test-pyxform:
	(cd converters/pyxform && \
	python3 pyxform/xls2xform.py ../test/1/input/form1.xlsx \
	../test/1/output/form1.xml)
test-survey-instance:
	python3 converters/test/2/test_SurveyInstance.py
test-xform-test:
	(cd converters/pyxform && \
	python3 pyxform/xls2xform.py ../test/XformTest/input/XformTest1.xlsx \
	../test/XformTest/output/XformTest1.xml)
test-unit-tests:
	python3 test/test.py

#test: test-pyxform
#test: test-survey-instance
#test: test-xform-test
test: test-unit-tests

# Docs
typical-sphinx-setup:
	sphinx-quickstart
open-docs:
	open docs/build/html/index.html
readme-to-docs:
	cp README.md docs/source/content/docs.md
test-files-to-static-doc-files:
	cp test/files/XformTest/input/XformTest1.xlsx docs/source/_static/xlsx_example.xlsx
build-docs-no-open:
	rm -rf docs/build/ && \
	make readme-to-docs && \
	make test-files-to-static-doc-files && \
	(cd docs && \
	make html)
build-docs:
	make build-docs-no-open && \
	make open-docs
docs-push-production:
	aws s3 sync docs/build/html s3://xform-test.pma2020.org --region us-west-2\
	 --profile work
docs-push-staging:
	aws s3 sync docs/build/html s3://xform-test-staging.pma2020.org --region\
	 us-west-2 --profile work
docs-push:
	make docs-push-staging && \
	make docs-push-production

create-docs: build-docs
docs-create: build-docs
docs-build: build-docs
docs: build-docs
docs-open: open-docs
push-docs: docs-push
push-docs-staging: docs-push-staging
push-docs-production: docs-push-production
