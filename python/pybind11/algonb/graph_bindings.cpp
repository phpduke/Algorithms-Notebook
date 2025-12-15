#include <pybind11/pybind11.h>
#include <pybind11/stl.h>

// Include original algorithm code
#include "../../../GraphX.cpp"

namespace py = pybind11;

PYBIND11_MODULE(graph, m) {
    m.doc() = "Python bindings for GraphX (Graph + BFS)";

    py::class_<Graph>(m, "Graph")
        .def(py::init<int, bool>(),
             py::arg("n"),
             py::arg("is_directed") = true)
        .def("add_edge",
             py::overload_cast<int, int, ll>(&Graph::add_edge),
             py::arg("u"), py::arg("v"), py::arg("cost") = 0)
        .def("add_edge",
             py::overload_cast<
                 std::tuple<int,int>,
                 std::tuple<int,int>, ll>(&Graph::add_edge),
             py::arg("u"), py::arg("v"), py::arg("cost") = 0)
        .def("add_edge",
             py::overload_cast<
                 std::tuple<int,int,int>,
                 std::tuple<int,int,int>, ll>(&Graph::add_edge),
             py::arg("u"), py::arg("v"), py::arg("cost") = 0);

    py::class_<BFS>(m, "BFS")
        .def(py::init<Graph*>())
        .def("run", py::overload_cast<int>(&BFS::run))
        .def("run", py::overload_cast<std::tuple<int,int>>(&BFS::run))
        .def("run", py::overload_cast<std::tuple<int,int,int>>(&BFS::run))
        .def("min_dist", py::overload_cast<int>(&BFS::min_dist))
        .def("is_visited", py::overload_cast<int>(&BFS::is_visited));
}
