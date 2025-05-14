# Para compilar

javac -d out/production/GasolinaCalc src/pacotecalculadora/GasolinaCalc.java

# Empacotar

jar cfm GasolinaCalc.jar MANIFEST.MF -C out/production/GasolinaCalc/ .

# Executar

java -jar GasolinaCalc.jar

# Criar o .exe

  jpackage ^
  --name CalculadoraGastoGasolina ^
  --input . ^
  --main-jar GasolinaCalc.jar ^
  --main-class pacotecalculadora.GasolinaCalc ^
  --type exe ^
  --icon lib/icone-calculadora.ico

