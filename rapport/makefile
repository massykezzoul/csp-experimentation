NAME=rapport

all:
	@pdflatex ${NAME}.tex

see:
	@evince ${NAME}.pdf &

clean:
	rm ${NAME}.aux ${NAME}.log ${NAME}.out ${NAME}.toc
mrproper: clean
	rm ${NAME}.pdf