This project is inspired by an arcade mobile game Atomic Bomber. For more information check Wiki.
Tried to use MVC in application design.

# important

It's a first year project, I was young, reckless and working on a Windows machine. I've hardcoded some filepaths for the Windows filesystem (e.g. "Pictures\\tank_picture.jpg"). Meanwhile on a unix-like system it should look like "Pictures/tank_picture.jpg". The obvious solution is to use a function from some java module for interaction with the OS to concatenate the directory name with the filename so it will be translated correctly for the corresponding OS by java itself. But I don't want to. Let's do it in a stupid way.

If you want to run the game on linux/mac, execute the following line in the terminal. It will change all occurences of "\\\\" to "/" recursively across all the files in the subdirectories.

`find /path/to/project -type f -exec perl -pi -e 's|\\\\|/|g' {} +`
