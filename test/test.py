"""Tests for Xform Test Suite"""
import os
import subprocess
# import sys


TEST_DIR = os.path.dirname(os.path.realpath(__file__)) + '/'
TEST_FILES_DIR = TEST_DIR + 'files/'
PROJECT_ROOT_DIR = TEST_DIR + '../'
TEMP_PYXFORM_DIR = PROJECT_ROOT_DIR + 'research/converters/pyxform/'
# sys.path.append(TEMP_PYXFORM_DIR)
# from pyxform.xls2xform import main_cli as pyxform_cli
# TEMP_PYXFORM_CLI_MODULE = TEMP_PYXFORM_DIR + 'pyxform.xls2xform'


def temp_test():
    """Temporary test while in development phase."""
    # pyxform_cli()
    this_test_dir = TEST_FILES_DIR + 'XformTest/'
    # subprocess.call(['rm', '-rf', this_test_dir + 'output/'])
    # os.makedirs(this_test_dir + 'output/')
    command = ['python3', '-m', 'pyxform.xls2xform',
               this_test_dir+'input/XformTest1.xlsx',
               this_test_dir+'output/XformTest1.xml']
    subprocess.call(command)


if __name__ == '__main__':
    temp_test()
