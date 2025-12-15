# Algorithms-Notebook
# For Python users
Below is a **clean, copy-paste ready README.md** written for **your exact setup**.
No assumptions, no extra fluff.

---

```md
# Algorithms Notebook — Python GraphX Bindings

This repository contains C++ algorithm implementations with **Python bindings using pybind11**.  
Currently, it exposes the **GraphX (Graph + BFS)** module for use directly in Python.

---

## 📁 Relevant Structure

```

Algorithms-Notebook/
│
├── GraphX.cpp                     # Original C++ Graph + BFS implementation
│
├── python/
│   └── pybind11/
│       ├── algonb/
│       │   ├── **init**.py
│       │   └── graph_bindings.cpp  # pybind11 bindings for GraphX
│       └── setup.py                # Build script
│
└── README.md

````

---

## 🧩 What Is Exposed to Python

### Python Module
```python
algonb.graph
````

### Classes

* `Graph`
* `BFS`

---

## 🛠️ Prerequisites

### 1. Python

* Python **3.10+**
* `pip` installed

### 2. Compiler (IMPORTANT)

#### Option A — Windows (Recommended)

Install **Microsoft C++ Build Tools**

* Download: [https://visualstudio.microsoft.com/visual-cpp-build-tools/](https://visualstudio.microsoft.com/visual-cpp-build-tools/)
* Select:

  * ✔ MSVC v14.x
  * ✔ Windows 10/11 SDK
  * ✔ C++ CMake tools

#### Option B — Linux / WSL

```bash
sudo apt update
sudo apt install build-essential python3-dev
```

---

## 📦 Build Instructions

### Step 1 — Create Virtual Environment

```bash
python -m venv venv
```

Activate:

**Windows**

```powershell
venv\Scripts\activate
```

**Linux / WSL**

```bash
source venv/bin/activate
```

---

### Step 2 — Install Dependencies

```bash
pip install pybind11 setuptools wheel
```

---

### Step 3 — Build the Extension

```bash
cd python/pybind11
python setup.py build_ext --inplace
```

After this, a compiled file like below will appear:

```
algonb/graph*.pyd   (Windows)
algonb/graph*.so    (Linux)
```

---

## 🚀 Using GraphX in Python

### Example Usage

```python
from algonb.graph import Graph, BFS

g = Graph(5, is_directed=False)

g.add_edge(0, 1)
g.add_edge(1, 2)
g.add_edge(2, 3)

bfs = BFS(g)
bfs.run(0)

print(bfs.min_dist(3))      # shortest distance
print(bfs.is_visited(4))    # False
```

---

## 🧠 Design Notes

* Original algorithm logic is **untouched**
* Python bindings are thin wrappers using **pybind11**
* No runtime Python overhead for BFS/Graph logic
* Designed for competitive programming & algorithm prototyping

---

## ❌ What Is NOT Committed

* `venv/`
* Compiled `.pyd / .so` files
* Local build artifacts

These are intentionally excluded via `.gitignore`.

---

## 📌 Future Extensions

* DFS
* Dijkstra
* Union-Find
* Segment Trees
* Python-friendly algorithm notebook interface

---

## 👤 Author

**Govind Mohanty**
NIT Rourkela
Algorithms Notebook Project

````

---

## NEXT STEP (ONLY THIS)
1. Paste this into `README.md`
2. Save
3. Run:
```bash
git add README.md
git commit -m "Add GraphX Python build and usage documentation"
git push origin pybind-graphx
````

Say **“README committed”** when done.
