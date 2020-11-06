#!/usr/bin/env python3

import sys
import os

"""
    usage : python3 benchmarkgen executable chemin_vers_sortie
"""

n = 35 # nombre de variables
d = 17 # taille des domaines
c = 249 # nombre de contrainte   
tmax, tmin = 288, 1# nombre de tuples (reduire de 3 à chaque étape jusqu'a 178)

if len(sys.argv) != 3 :
    print("usage : python3 benchmarkgen executable chemin_vers_sortie")
    print("\texecutable: Le générateur de CSP binaire.")
    print("\tchemin_vers_sorie: chemin oû stocker le benchmark")
    sys.exit(1)

executable = sys.argv[1]

dir_return = sys.argv[2]
if dir_return[-1] == '/':
    dir_return = dir_return[:-1]

def durete(d, t):
    return ((d*d) - t)/(d*d)

def cspgen(n, d, c, t, file):
    # Génère une instance de CSP
    cmd = "./{} {} {} {} {} 1 > ./{}/{} 2> /dev/null".format(executable, n, d, c, t, dir_return, file)
    return os.system(cmd)


def genall():
    nombre_instance = 10
    for t in range(tmax, tmin - 1, -3): 
        dur = int(durete(d,t) * 100)
        for i in range(nombre_instance): 
            file_name = "d{}-i{}.txt".format(dur, i+1)
            if cspgen(n, d, c, t, file_name):
                print("Dureté {}: {}/{}".format(dur, i+1, nombre_instance))
            else:
                print("Erreur: dureté {}: {}/{}".format(dur, i+1, nombre_instance))
                
    return

genall()