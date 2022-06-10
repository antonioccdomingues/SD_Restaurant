echo "Transfering data to the Chef node."
#sshpass -p "qwerty" ssh sd205@l040101-ws04.ua.pt 'mkdir -p assignment2/restaurant'
#sshpass -p "qwerty" ssh sd205@l040101-ws04.ua.pt 'rm -rf assignment2/restaurant/*'
sshpass -p "qwerty" scp genclass.jar chef.zip sd205@l040101-ws04.ua.pt:/home/sd205
echo "Decompressing data sent to the bar node."
sshpass -p "qwerty" ssh sd205@l040101-ws04.ua.pt 'unzip -qo chef.zip'
echo "Executing program at the customers node."
sshpass -p "qwerty" ssh sd205@l040101-ws04.ua.pt './chef.sh' 