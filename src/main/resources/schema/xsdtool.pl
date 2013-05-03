open( FH, "TR069_AM4.xsd" );
open( OUTFILE, ">TR069_AM4_Out.xsd");
foreach $line ( <FH> ){
	if ($line =~ /^ *\d+ *.*/) {
		$line =~ s/^ *\d+ *(.*)/$1/;
		print OUTFILE $line;
	}
}
close(OUTFILE);
close(FH);
