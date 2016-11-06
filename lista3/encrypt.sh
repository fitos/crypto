#! /bin/sh

#### How to use ####:
## Encrypt by:
# ./encrypt.sh aes cbc e [KeyStore] [KeyID] [FileToEncrypt] [EncryptedFile]
## Decrypt by
# ./encrypt.sh aes cbc d [KeyStore] [KeyID] [EncryptedFile] [DecryptedFile]
## Generate key to keystore
# keytool -genkey -alias [KeyID] -keyalg RSA -keystore [KeyStore] -keysize 2048
####

echo "Please enter password to your key";
stty -echo
read password;
stty echo

keytool -importkeystore -srckeystore $4 -destkeystore KeyStore.p12 -deststoretype PKCS12 -srcalias $5 -deststorepass $password -destkeypass $password

openssl pkcs12 -in KeyStore.p12  -nodes -nocerts -out key.pem
openssl rsa -outform der -in key.pem -out key.key


openssl $1-256-$2 -$3 -a -kfile key.key -iv 12345678123456781234567812345678 -in $6 -out $7	

rm -f key.key key.pem KeyStore.p12
