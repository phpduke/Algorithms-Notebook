from setuptools import setup, Extension
import pybind11

ext_modules = [
    Extension(
        "algonb.graph",
        ["algonb/graph_bindings.cpp"],
        include_dirs=[pybind11.get_include()],
        language="c++",
    )
]

setup(
    name="algonb",
    version="0.1.0",
    packages=["algonb"],
    ext_modules=ext_modules,
)
    