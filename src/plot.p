# Gnuplot script file for plotting data in file "plot.dat"
# This file is called   plot.p
set terminal pngcairo
set output "graph.png"
set autoscale                        # scale axes automatically
set xtic auto                          # set xtics automatically
set ytic auto                          # set ytics automatically
set title "Temps d'éxecution en ms en fonction de la dureté"
set xlabel "Dureté (%)"
set ylabel "Temps d'éxecution (ms)"
plot "plot.dat" using 1:2 title "Temps execution" w l , \
            "plot.dat" using 1:3 title "% instance ayant au moins une solution" w l
