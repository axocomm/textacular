TEXFILE = slides

.PHONY: all dvi pdf rotate clean

all: dvi pdf ps

dvi: $(TEXFILE).dvi
ps: $(TEXFILE).ps
pdf: $(TEXFILE).pdf

DISTDIR = dist

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

rotate: $(TEXFILE).pdf
	pdftk $< cat 1-endR output $(DISTDIR)/$<

clean:
	@rm -f $(TEXFILE).aux $(TEXFILE).log $(TEXFILE).out
