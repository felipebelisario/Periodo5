import time

def sf(n):
    if n == 0: 
        f = 0
    elif n == 1:
        f = 1
    else:
        pprev = 0
        prev = 1
        f = 0
        for i in range(2, n):
            f = prev + pprev
            pprev = prev
            prev = f
    return (f)


for i in range(0, 45):
    start = time.time()
    print(sf(i))
    end = time.time()
    #print(end - start)
