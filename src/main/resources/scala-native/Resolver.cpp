#include <dlfcn.h>
#include <iostream>

class Resolver
{
    static void * findFunctionPointer(char const * name);
};

void * Resolver::findFunctionPointer(char const * name)
{
    void * handle = dlopen(nullptr, RTLD_LAZY);

    if (!(handle)) {
        std::cerr << "Failed to open executable: " << dlerror() << '\n';
        return nullptr;
    }

    void * symbol_address = dlsym(handle, name);

    if (!(symbol_address)) {
        std::cerr << "Failed to find symbol: " << dlerror() << '\n';
        dlclose(handle);
        return nullptr;
    }

    dlclose(handle);

    return symbol_address;
}
