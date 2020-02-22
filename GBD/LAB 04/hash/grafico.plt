# executar esse arquivo com GNUPLOT
# http://www.gnuplot.info/download.html
# no linux: gnuplot grafico.plt
# no windows: wgnuplot grafico.plt
set title "Histograma"
set encoding iso_8859_1
set xlabel "Hash"
set ylabel "Quantidade"
plot '-' title 'Quantidade' with linespoints linewidth 2 linetype 1 pointtype 1
0 97
1 120
2 110
3 110
4 105
5 110
6 111
7 108
8 115
9 111
10 95
11 115
12 114
13 108
14 119
15 106
16 101
17 99
18 115
19 106
20 121
21 118
22 100
23 98
24 99
25 121
26 104
27 200
28 96
29 92
30 103
31 126
32 101
33 132
34 109
35 120
36 124
37 114
38 116
39 97
40 106
41 127
42 120
43 117
44 106
45 132
46 90
47 101
48 101
49 104
end
pause -1
