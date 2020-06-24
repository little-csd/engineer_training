export SRC_PROTO=./proto
export JAVA_DST=app/src/main/java
export PYTHON_SRC=../Server/proto

rm $JAVA_DST/com/example/aiins/proto/*.java
rm $PYTHON_SRC/*.py

protoc -I=./ --java_out=$JAVA_DST proto/*.proto
protoc -I=$SRC_PROTO --python_out=$PYTHON_SRC $SRC_PROTO/*.proto
