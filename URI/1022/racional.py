qnt = int(input())

def simplifica(num, den):

        i = 2

        while 1:

            num2 = num % i
            den2 = den % i

            if num2 == 0 and den2 == 0: 
                num = num / i
                den = den / i

            i = i+1    

            if i > num and i > den:
                break

        return int(num), int(den)

for i in range(0,qnt):
    exp = str(input())

    exp = exp.split()

    if exp[3] == '+':
        num = int(exp[0]) * int(exp[6]) + int(exp[4]) * int(exp[2])
        den = int(exp[2]) * int(exp[6])
    elif exp[3] == '-':
        num = int(exp[0]) * int(exp[6]) - int(exp[4]) * int(exp[2])
        den = int(exp[2]) * int(exp[6])
    elif exp[3] == '*':
        num = int(exp[0]) * int(exp[4])
        den = int(exp[2]) * int(exp[6])
    elif exp[3] == '/':
        num = int(exp[0]) * int(exp[6])
        den = int(exp[4]) * int(exp[2])

    print(str(num) + "/" + str(den), end='')

    num,den = simplifica(num,den)

    print(" = " + str(num) + "/" + str(den))