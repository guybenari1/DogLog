package superapp.miniApps.getParks;

import java.util.Arrays;

import superapp.boundaries.InvokedByBoundary;

public class GoogleMapResponse {
    private PlaceResult[] results;
    private InvokedByBoundary invokedBy;

    public InvokedByBoundary getInvokedBy() {
		return invokedBy;
	}

	public void setInvokedBy(InvokedByBoundary invokedBy) {
		this.invokedBy = invokedBy;
	}

	public PlaceResult[] getResults() {
        return results;
    }

    public void setResults(PlaceResult[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GoogleMapResponse [results=" + Arrays.toString(results) + "]";
    }

}