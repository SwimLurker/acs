open( FH, "TR-069AM1.xsd" );
open( OUTFILE, ">TR-069AM1_Out.xsd");
foreach $line ( <FH> ){
$line =~ s/ *\d+ *(.*)/$1/;
print OUTFILE $line;
}
close(OUTFILE);
close(FH);
