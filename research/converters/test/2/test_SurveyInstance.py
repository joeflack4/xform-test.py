"""Checking out how SurveyInstance works."""
import os
import sys
sys.path.append(sys.path[0] + '/../..pyxform')

# noinspection PyUnresolvedReferences,PyPackageRequirements
from pyxform.instance import SurveyInstance


DIR = os.path.dirname(os.path.abspath(__file__))
INPUT_DIR = DIR + '/input/'


# - Attempt 1
# my_survey = SurveyInstance()
# python3 converters/test/2/test_SurveyInstance.py
# Traceback (most recent call last):
#   File "converters/test/2/test_SurveyInstance.py", line 9, in <module>
#     my_survey = SurveyInstance()
# TypeError: __init__() missing 1 required positional argument: 'survey_object'

# - Attempt 2
# from pyxform.survey import Survey
# my_survey = Survey().instantiate()
# File "/Users/joeflack4/projects/xform-test/converters/pyxform/pyxform/
# survey_element.py", line 132, in validate
#     raise PyXFormError(msg)
# pyxform.errors.PyXFormError: The name '' is an invalid xml tag. Names must
# begin with a letter, colon, or underscore, subsequent characters can include
# numbers, dashes, and periods.

# - Attempt 3
# my_survey = SurveyInstance.import_from_xml(None, INPUT_DIR + 'form1.xlsx')
# xml.parsers.expat.ExpatError: not well-formed (invalid token): line 1,
# column 0
# make: *** [test-survey-instance] Error 1

# - Attempt 4
# Options:
#   - I should just use the test/j2x_test_instantiation
#   - I could modify the instance thingy.
#   - I could just forget about this and move on to looking at JavaRosa.

