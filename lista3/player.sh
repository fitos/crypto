#! /bin/bash
configFileName="playerConfig.txt.enc"

k2="256541e34a15df8997597cbeec7e8591c48270831cabd7712cea03d08225ef2e"

fileExists=$(find -name "$configFileName" | wc -l)

if [ $fileExists -lt 1 ]; then
	# Creating ping
	echo "Please enter PIN to your player!";
	stty -echo
	read pin;
	stty echo

	echo "Please enter you KeyStore location";
	read keyStore
	
	echo "Please enter your key alias";
	read keyAlias
	
	echo "Please enter password to your key";
	stty -echo
	read password;
	stty echo
	
	configContent=$(echo -e "$keyStore\n$keyAlias\n$password\n$pin")
	
	openssl aes-256-cbc -e -base64 -K $k2 -iv 12345678123456781234567812345678 <<< "$configContent" -out playerConfig.txt.enc
	
	
else
	# Checking pin
	echo "Please enter PIN to your player";
	stty -echo
	read pin2;
	stty echo
	
	config=$(openssl aes-256-cbc -d -base64 -K $k2 -iv 12345678123456781234567812345678 -in playerConfig.txt.enc)
		
	i=0
	while IFS='' read -r line || [[ -n "$line" ]]; do
   		if [ "$i" == 0 ]; then
   			keyStore="$line"
   		elif [ "$i" == 1 ]; then
   			keyAlias="$line"
   		elif [ "$i" == 2 ]; then
   			password="$line"
   		else 
   			pin="$line"
   			if [ "$pin" == "$pin2" ]; then
				echo "Successfull verification!"
			else 
				echo "Wrong pin!"
				exit
			fi
   		fi
   		i=$(( $i + 1 ))
	done <<< "$config"
	
fi

keytool -importkeystore -srckeystore $keyStore -destkeystore KeyStore.p12 -deststoretype PKCS12 -srcalias $keyAlias -deststorepass $password -destkeypass $password

openssl pkcs12 -in KeyStore.p12  -nodes -nocerts -out key.pem
openssl pkcs8 -topk8 -inform PEM -outform DER -nocrypt -in key.pem -out p8key.pem
#openssl rsa -outform der -in key.pem -out key.key

#keypem=$(openssl pkcs12 -in KeyStore.p12  -nodes -nocerts)
#keykey=$(openssl rsa -outform der -in "$keypem")

openssl aes-256-cbc -d -a -kfile p8key.pem -iv 12345678123456781234567812345678 -in "$1" -out song.mp3

play song.mp3

rm -f key.key key.pem KeyStore.p12 song.mp3







	
