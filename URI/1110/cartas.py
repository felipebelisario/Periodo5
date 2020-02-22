while 1:

    cartas = int(input())

    if cartas == 0:
        exit(0)

    seq = list(range(1,cartas+1))

    print("Discarded cards: ", end='')

    while len(seq) > 1:

        if len(seq) > 2:
            print(str(seq[0]) + ", ", end='')
        else:
            print(str(seq[0]))
        
        seq.pop(0)

        seq.append(seq[0])
        seq.pop(0)

        
    print("Remaining card: " + str(seq[0]))