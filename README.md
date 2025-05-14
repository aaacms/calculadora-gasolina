# Calculadora de Gasto de Gasolina por viagem

Esta aplicação surgiu da necessidade de meu avô de calcular seus ganhos como taxista. Vi sua dificuldade de realizar as contas de autonomia e receita da semana, e resolvi criar esta pequena aplicação em Java para facilitar o seu dia-a-dia. Apesar de simples, a aplicação entrega um grande valor. Visto que ele não possui Java ou qualquer outra ferramenta instalada em seu computador, criar um arquivo executável foi a forma mais simples encontrada para solucionar o seu problema.

<video src="lib/video-demo.mp4" controls="controls" style="max-width: 730px;">
</video>

---
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

