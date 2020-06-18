export SRC_PROTO=./proto
export JAVA_DST=app/src/main/java/
export PYTHON_SRC=../Server/

protoc -I=./ --java_out=$JAVA_DST proto/*.proto
protoc -I=$SRC_PROTO --python_out=$PYTHON_SRC $SRC_PROTO/*.proto
