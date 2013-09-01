%--------------------------------------------------------------------------
clear; clc; close all;
%--------------------------------------------------------------------------
% Filtragem em oitavas
fs = 8000;

fCorte1 = 125;
fCorte2 = 2 * 125;
fout = fopen('octaveBandFilter_OnsetDetection.xml','wt');
fprintf(fout, '<octaveBandFilter>');
for nbandas = [1 2 3 4 5]
    %----------------------------------------------------------------------
    fCorte = [fCorte1 fCorte2];
    [b a]  = butter(4, fCorte/fs, 'bandpass');
    %----------------------------------------------------------------------
    fprintf(fout, '\n<filter>');
    fprintf(fout, '\n<f1>%.4f</f1>', fCorte1);
    fprintf(fout, '\n<f2>%.4f</f2>', fCorte2);
    fprintf(fout, '\n<b>');
    for i=1:length(b)
        if (i > 1) 
            fprintf(fout, ', ');
        end
        fprintf(fout, '%.10f', b(i));
    end
    fprintf(fout, '</b>');
    fprintf(fout, '\n<a>');
    for i=1:length(a)
        if (i > 1)
            fprintf(fout, ', ');
        end
        fprintf(fout, '%.10f', a(i));
    end
    fprintf(fout, '</a>');
    fprintf(fout, '\n</filter>');
    %----------------------------------------------------------------------
    fCorte1 = fCorte2;
    fCorte2 = 2 * fCorte2;
    %----------------------------------------------------------------------
end
fprintf(fout, '\n</octaveBandFilter>');
fclose(fout);
%--------------------------------------------------------------------------
% fprintf('\nWrite data to a file, no screen output:\n');
% fout = fopen('someData.txt','wt');
% fprintf(fout,'j\tx(j)\ty(j)\tz(j)\n');
% for j=1:length(x)
% fprintf(fout,'%d\t%e\t%e\t%e\n',j,x(j),y(j),z(j));
% end
% fclose(fout); % always close the open file handle
%--------------------------------------------------------------------------
