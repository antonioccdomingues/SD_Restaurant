echo "Transfering data to the Bar node."
#sshpass -p "qwerty" ssh sd205@l040101-ws10.ua.pt 'mkdir -p assignment2/restaurant'
#sshpass -p "qwerty" ssh sd205@l040101-ws10.ua.pt 'rm -rf assignment2/restaurant/*'
sshpass -p "qwerty" ssh sd205@l040101-ws10.ua.pt 'pkill -U sd205 java'
sshpass -p "qwerty" scp genclass.jar dirGeneralRepos.zip sd205@l040101-ws10.ua.pt:/home/sd205
echo "Decompressing data sent to the bar node."
sshpass -p "qwerty" ssh sd205@l040101-ws10.ua.pt 'unzip -qo dirGeneralRepos.zip'
echo "Executing program at the customers node."
sshpass -p "qwerty" ssh sd205@l040101-ws10.ua.pt 'cd dirGeneralRepos ; ./generalRepos.sh' 
#sshpass -p "qwerty" ssh sd205@l040101-ws10.ua.pt 'cat logger' 
