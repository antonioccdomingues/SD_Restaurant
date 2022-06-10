echo "Transfering data to the Bar node."
# sshpass -p "qwerty" ssh sd205@l040101-ws01.ua.pt 'mkdir -p assignment2/restaurant'
# sshpass -p "qwerty" ssh sd205@l040101-ws01.ua.pt 'rm -rf assignment2/restaurant/*'
sshpass -p "qwerty" ssh sd205@l040101-ws01.ua.pt 'pkill -U sd205 java'
sshpass -p "qwerty" scp genclass.jar dirBar.zip sd205@l040101-ws01.ua.pt:/home/sd205
echo "Decompressing data sent to the bar node."
sshpass -p "qwerty" ssh sd205@l040101-ws01.ua.pt 'unzip -qo dirBar.zip'
echo "Executing program at the customers node."
sshpass -p "qwerty" ssh sd205@l040101-ws01.ua.pt 'cd dirBar ; ./bar.sh' 
