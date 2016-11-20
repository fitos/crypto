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
openssl pkcs8 -topk8 -inform PEM -outform DER -nocrypt -in key.pem -out p8key.pem
#openssl rsa -outform der -in key.pem -out key.key


openssl $1-256-$2 -$3 -a -kfile p8key.pem -iv 12345678123456781234567812345678 -in $6 -out $7	

rm -f p8key.pem key.pem KeyStore.p12
