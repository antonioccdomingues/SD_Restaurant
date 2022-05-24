echo "Transfering data to the Kitchen node."
#sshpass -p "qwerty" ssh sd205@l040101-ws02.ua.pt 'mkdir -p assignment2/restaurant'
#sshpass -p "qwerty" ssh sd205@l040101-ws02.ua.pt 'rm -rf assignment2/restaurant/*'
sshpass -p "qwerty" ssh sd205@l040101-ws02.ua.pt 'pkill -U sd205 java'
sshpass -p "qwerty" scp genclass.jar kitchen.zip sd205@l040101-ws02.ua.pt:/home/sd205
echo "Decompressing data sent to the bar node."
sshpass -p "qwerty" ssh sd205@l040101-ws02.ua.pt 'unzip -qo  kitchen.zip'
echo "Executing program at the customers node."
sshpass -p "qwerty" ssh sd205@l040101-ws02.ua.pt 'java -classpath "genclass.jar:." serverSide.main.KitchenMain' 
