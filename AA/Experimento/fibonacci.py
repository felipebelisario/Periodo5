import time

def f(n):
    if n == 0: return (0)
    if n == 1: return (1)
    return (f(n-1) + f(n-2))

for i in range(0, 45):
    start = time.time()
    f(i)
    end = time.time()
    print(end - start)
