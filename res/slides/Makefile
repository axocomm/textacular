TEXFILE = slides

.PHONY: all dvi pdf clean

all: dvi pdf ps

dvi: $(TEXFILE).dvi
ps: $(TEXFILE).ps
pdf: $(TEXFILE).pdf

$(TEXFILE).dvi: $(TEXFILE).tex
	( \
		latex $<; \
		while grep -q "Rerun to get cross-references right." $(<:.tex=.log); do \
			latex $<; \
		done \
	)

$(TEXFILE).ps: $(TEXFILE).dvi
	dvips -q $< -o $(<:.dvi=.ps)

$(TEXFILE).pdf: $(TEXFILE).ps
	ps2pdf -dPDFSETTINGS=/prepress $<

clean:
	@rm -f $(TEXFILE).aux $(TEXFILE).log $(TEXFILE).out
