echo "Transfering data to the Table node."
sshpass -p "qwerty" ssh sd205@l040101-ws03.ua.pt 'mkdir -p assignment2/restaurant'
sshpass -p "qwerty" ssh sd205@l040101-ws03.ua.pt 'rm -rf assignment2/restaurant/*'
sshpass -p "qwerty" scp genclass.jar table.zip sd205@l040101-ws03.ua.pt:/home/sd205/assignment2/restaurant
echo "Decompressing data sent to the bar node."
sshpass -p "qwerty" ssh sd205@l040101-ws03.ua.pt 'cd assignment2/restaurant ; unzip -qo table.zip'
echo "Executing program at the customers node."
sshpass -p "qwerty" ssh sd205@l040101-ws03.ua.pt 'java -classpath "genclass.jar:." serverSide.main.TableMain' 
