#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""Command Line Interface."""
from argparse import ArgumentParser
from sys import stderr

from xform_test.definitions.abstractions import chain
from xform_test import run


def _required_fields(parser):
    """Add required fields to parser.

    Args:
        parser (ArgumentParser): Argparse object.

    Returns:
        ArgumentParser: Argeparse object.
    """
    # File
    #   type:'string'
    file_help = 'Path to source XLSForm(s) or XForm(s).'
    parser.add_argument('files', nargs='+', help=file_help)
    return parser


def _optional_fields(parser):
    """Add CLI-only fields to parser.

    Args:
        parser (ArgumentParser): Argparse object.

    Returns:
        ArgumentParser: Argeparse object.
    """
    out_help = ('Path (including file name) to save converted file if 1 file, '
                'else path to directory for multiple files, in which case file'
                ' names will be automatically generated.\n\nIf this argument '
                'is not supplied, then STDOUT is used.')
    parser.add_argument('-o', '--outpath', help=out_help)
    return parser


def cli():
    """Command line interface for package.

    Usage:
        `python3 -m xlsform-test <path/to/file> <options>`

    Args:
        n/a

    Returns:
        n/a

    Side Effects:
        Executes program.
    """
    prog_desc = 'Runs automated tests on a form.'

    argeparser = ArgumentParser(description=prog_desc)
    parser = chain(argeparser, funcs=[_required_fields, _optional_fields])
    args = parser.parse_args()

    try:
        run(files=args.files, outpath=args.outpath)
    except Exception as err:
        err = 'An error occurred:\n{}'.format(err)
        print(err, file=stderr)


if __name__ == '__main__':
    cli()
