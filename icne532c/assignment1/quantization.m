clc;
clear all;
t = 0:0.1:2*pi;

signal = sin(t);

partition = -1:0.2:1;

cb = -1.2:0.2:1;

[index,quants] = quantiz(signal,partition,cb);

plot(t,signal,t,quants,'-')
xlabel("time");
ylabel("Amplitude");
title("Quantization");
legend('Original signal','Quantized signal');

axis([-.2 7 -1.2 1.2]);