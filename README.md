# Toy Assembler

A basic assemby language in implemented in Java.

## Documentation

### Registers

There are 10 registers in total:
- Hard wired zero `Z0`: All reads return 0 and writes are not permitted, retuning an error.
- One function return register `V0`: All functions store the result of their operations in this register, it is writable, but unwise to do so.
- Three function argument registers `F0 F1 F2`: Registers for holding function arguments.
- Five general purpose registers `R0 R1 R2 R3 R4`: Registers without any specific purpose, for general use.

### Behaviour

All functions write their result in `V0`.  
The function argument registers exist only to help in organizing working data, and can be used for any purpose the user wants.

### Instructions

- `SET M N`: Set Memory cell number `M` with number `N`.
- `LOAD R M`: Load the value of memory cell `M` into register `R`.
- `STORE R M`: Store the value of register `R` into memory cell `M`.
- `ADD F0 F1`: Add the values of `F0` and `F1`, store the value in `V0`.
- `MUL F0 F1`: Multiply the values of `F0` and `F1`, store the value in `V0`.
- `SUB F0 F1`: Subtract the value of `F1` from `F0`, store the value in `V0`.
- `DIV F0 F1`: Divide the value of `F0` by `F1`, store the value in `V0`.
- `LABEL L`: Set a label with name `L`.
- `JUMP L`: Jump to label `L`.
- `JZERO L R`: Jump to label `L` if `R` is NOT zero.
