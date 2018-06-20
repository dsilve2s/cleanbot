@echo off
if not exist "target\nxj-classes\" (
	mkdir target\nxj-classes
)
call nxjc -d target/nxj-classes src/main/java/Main.java
cd target/nxj-classes
call nxjlink -o ../cleanbot.nxj Main
cd ..
call nxjupload -r cleanbot.nxj
cd ..