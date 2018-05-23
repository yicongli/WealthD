##The way to excute the code in command line:

1.cd to the code folder

2.execute "javac -d bin -sourcepath src -cp lib/jfreechart-1.0.19.jar:lib/jcommon-1.0.23.jar src/MainClass.java"

3.execute "java -cp bin:lib/jcommon-1.0.23.jar:lib/jfreechart-1.0.19.jar MainClass" 

Or execute runnable jar package directly
java -jar main.jar 


##To run model with parameters:

Add Parameters after executing the java with the order below:
personNum       
visionMax   
metabolismMax 
lifeExpectancyMin 
lifeExpectancyMax 
percentBestLand     
grainGrowthInterval 
numGrainGrown    

For example:   
java -cp bin:lib/jcommon-1.0.23.jar:lib/jfreechart-1.0.19.jar MainClass 250 5 10 1 80 10 1 1
or
java -jar main.jar 250 5 10 1 80 10 1 1