"""Init."""
# import json
from subprocess import call


from xform_test.config import JAVAROSA_BINARY_PATH


# def mock_data():
#     """Returns mock data in JSON format."""
#     obj = {
#         "nodes": [
#             {"nodeId": "age",
#              "label": "Enter the respondent's age.",
#              "pointOfFailure": "constraint",
#              "failedAssertionExpression": ". > 18"},
#         ]
#     }
#     return json.dumps(obj)


# noinspection PyUnusedLocal
def run(files=[], outpath=None):
    """Run.

    Args:
        files (list): Path to source XLSForm(s) or XForm(s).
        outpath (string): Path (including file name) to save converted file if
         1 file, else path to directory for multiple files, in which case file
         names will be automatically generated.\n\nIf this argument is not
         supplied, then STDOUT is used.
    """
    # print(files, outpath)  # Temp
    call(['java', '-jar', JAVAROSA_BINARY_PATH, ' '.join(files)])


if __name__ == '__main__':
    from xform_test.interfaces.cli import cli
    cli()
