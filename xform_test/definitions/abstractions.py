"""Reusable function and class abstractions."""
# TODO: Add to an external library.
import inspect
import os


def pwd():
    """Get present working directory."""
    # return os.path.abspath(os.path.dirname(__file__))
    calling_file = os.path.abspath((inspect.stack()[1])[1])
    calling_dir = os.path.dirname(calling_file)
    return calling_dir


def chain(_input, funcs):
    """Execute recursive function chain on input and return it.

    Side Effects: Mutates input, funcs.

    Args:
        _input: Input of any data type to be passed into functions.
        funcs: Ordered list of funcs to be applied to input.

    Returns:
        Recusive call if any functions remaining, else the altered input.
    """
    return chain(funcs.pop(0)(_input), funcs) if funcs else _input
