echo "Generation du benchmark"
rm ../networks/d-*.txt
python3 benchmarkgen.py ../bin/cspgen ../networks/

cd csp/
javac *.java
echo "Generation de plot.dat"
java App ../../networks/ > ../plot.dat

cd ..
echo "Géneration du graph.png"
gnuplot -c plot.p